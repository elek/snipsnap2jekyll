/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.migrator;

import net.anzix.snipsnap2xwiki.MigrationContext;
import net.anzix.snipsnap2xwiki.MigrationSet;
import net.anzix.snipsnap2xwiki.Page;
import net.anzix.snipsnap2xwiki.transformation.Transformation;
import org.jdom.Element;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Migrate Snips (wiki pages).
 */
public class SnipMigrator extends AbstractObjectMigrator {

    private boolean migrateAttachments = true;

    private Set<String> ignorePages;

    private MigrationSet steps = new MigrationSet();

    public SnipMigrator(MigrationContext context) {
        super(context);

        ignorePages = new HashSet();
        ignorePages.add("start");
        ignorePages.add("start2");
        ignorePages.add("sandbox");
        ignorePages.add("snips-by-user");
        //todo
        ignorePages.add("JMX");
        ignorePages.add("singleton");
        ignorePages.add("WTF/Xmas");
        ignorePages.add("Xmas");
        ignorePages.add("archivum");
        ignorePages.add("oldposts");


        steps.add(new MigrationStep("name", "name", new Transformation() {

            @Override
            public String transform(String source, Page page) {
                return source.replaceAll("/", "");
            }
        }));

        steps.add(new MigrationStep("name", "title"));
        steps.add(new MigrationStep("mUser", "contentAuthor"));
        steps.add(new MigrationStep("mUser", "author"));
        steps.add(new MigrationStep("cUser", "creator"));

        steps.add(new MigrationStep("cTime", "creationDate"));
        steps.add(new MigrationStep("mTime", "date"));
        steps.add(new MigrationStep("mTime", "contentUpdateDate"));

        steps.add(new MigrationStep("content", "content", getContext().getSyntaxTransformation()));


    }

    @Override
    protected boolean includeInMigration(Element e) {

        if (e.getName().equals("user")) {
            return false;
        }
        String name = e.getChildText("name");
        if (name.equals("admin")) {
            return false;
        } else if (name.startsWith("comment") || name.startsWith("start/") || name.startsWith("topics/")) {
            return false;
        } else if (name.startsWith("SnipSnap") || name.startsWith("snipsnap")) {
            return false;
        } else if (ignorePages.contains(name)) {
            return false;
        } else if (getContext().getUserCache().containsKey(name)) {
            return false;
        }
        return true;
    }

    @Override
    public void migrateObject(Element oldRoot) throws Exception {

        String name = oldRoot.getChildText("name");

        if (name.contains("/")) {
            String parentName = name.substring(0, name.lastIndexOf("/"));
//            name = name.replaceAll("/", "");
            parentName = parentName.replaceAll("/", "");
        }
        //TODO: Arvizturo tukorfurogep
        if (name.contains("zt") && name.contains("rf")) {
            return;
        }
        System.out.println("Migration " + name);

        String cleanName = "";
        int i = 0;
        while (i < name.length()) {
            char ch = name.charAt(i);
            if ((ch & 0xC0) == 0xA0) {
                cleanName += "x";
            } else {
                cleanName += ch;
            }
            i++;
        }
        name = cleanName;
        System.out.println(name);

        Map<String, String> metaData = new HashMap<String, String>();

        //copy properties
        Page newPage = new Page();
        newPage.addMeta("layout", "wiki");
        steps.migrate(oldRoot, newPage);


        //write output
        File output = getFile("wiki", name);
        if (!output.getParentFile().exists()) {
            output.getParentFile().mkdirs();
        }
        newPage.write(output);
        getContext().pageMigratedSuccesfully("Main." + name);

    }

    @Override
    public void migrate(Element root) throws Exception {
        super.migrate(root);
        try (FileWriter writer = new FileWriter(getFile("wiki", "Missing"))) {
            writer.write("----\n");
            writer.write("title: TODO\n");
            writer.write("layout: default\n");
            writer.write("----\n");
            writer.write("# TODO\n\n");
            for (String missing : getContext().getMissingPages()) {
                writer.write("- [" + missing + "][" + missing + "]\n");
            }
        }

    }

    public void setMigrateAttachments(boolean migrateAttachments) {
        this.migrateAttachments = migrateAttachments;
    }

}
