/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.migator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.anzix.snipsnap2xwiki.transformation.AddPrefix;
import net.anzix.snipsnap2xwiki.DomCopier;
import net.anzix.snipsnap2xwiki.MigrationContext;
import net.anzix.snipsnap2xwiki.transformation.ReplaceTransformation;
import net.anzix.snipsnap2xwiki.transformation.Transformation;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author elek
 */
public class NewsMigrator extends AbstractObjectMigrator {

    Transformation xwikiPrefix = new AddPrefix("XWiki.");
    int n = 0;

    public NewsMigrator(MigrationContext context) {
        super(context);
    }

    @Override
    protected boolean includeInMigration(Element e) {
        if (e.getName().equals("user")) {
            return false;
        }
        String name = e.getChildText("name");
        return (name.startsWith("topics/") || name.startsWith("start/"));


    }

    @Override
    public void migrateObject(Element oldRoot) throws Exception {
        System.out.println(oldRoot.getChildText("name"));
        String name = oldRoot.getChildText("name");

        SAXBuilder builder = new SAXBuilder();
        Document d = builder.build(new File("src/main/template/Blog.xml"));
        Element newRoot = d.getRootElement();

        DomCopier copier = new DomCopier(oldRoot, newRoot);

        String title = getTitle(oldRoot);


        String newName = "Hir" + n++;
        copier.copyText("name", "title", new ReplaceTransformation(title));
        copier.copyText("name", "name", new ReplaceTransformation(newName));
        copier.copyText("mUser", "contentAuthor", xwikiPrefix);
        copier.copyText("mUser", "author", xwikiPrefix);
        copier.copyText("cUser", "creator", xwikiPrefix);

        copier.copyText("cTime", "creationDate");
        copier.copyText("mTime", "date");
        copier.copyText("mTime", "contentUpdateDate");

        copier.copyTextToObjectProperty("content", "XWiki.ArticleClass[0]", "content", getContext().getSyntaxTransformation());

        copier.copyTextToObjectProperty("name", "XWiki.ArticleClass[0]", "title", new ReplaceTransformation(title));

        copyComments(name, newRoot);
        fixObjextNames(newName, newRoot);

        writeFile("Main", newName, d);
        getContext().pageMigratedSuccesfully("Main." + newName);

    }

    private String getTitle(Element oldRoot) {
        try {
            String content = oldRoot.getChildText("content");
            String title = new BufferedReader(new StringReader(content)).readLine();
            if (title.startsWith("1 ")) {
                title = title.substring(2);
            }
            title = title.replaceFirst("\\{.*\\}", "");
            return title.trim();
        } catch (IOException ex) {
            Logger.getLogger(NewsMigrator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "???";

    }
}
