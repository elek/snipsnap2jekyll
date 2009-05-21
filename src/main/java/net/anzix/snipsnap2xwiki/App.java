package net.anzix.snipsnap2xwiki;

import java.io.File;
import java.io.IOException;
import net.anzix.snipsnap2xwiki.migator.GroupFileMigrator;
import net.anzix.snipsnap2xwiki.migator.NewsMigrator;
import net.anzix.snipsnap2xwiki.migator.PackageFileMigrator;
import net.anzix.snipsnap2xwiki.migator.SnipMigrator;
import net.anzix.snipsnap2xwiki.migator.UserMigrator;
import net.anzix.snipsnap2xwiki.transformation.MacroListTransformation;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * SnipSnap2XWiki migration spaghetti edition
 *
 * @todo the users can't modify own profile datas
 */
public class App {

    private File oldFile;

    private File newDir;

    public App(File oldFile, File newDir) {
        this.oldFile = oldFile;
        this.newDir = newDir;
    }

    public void migrate() throws Exception {
        try {
            SAXBuilder builder = new SAXBuilder();

            Document d = builder.build(oldFile);
            Element snipSnapDumpRoot = d.getRootElement();

            MigrationContext context = new MigrationContext();
            context.init(snipSnapDumpRoot);


            new File(newDir, "Main").mkdirs();
            new File(newDir, "XWiki").mkdirs();

            new UserMigrator(context).migrate(snipSnapDumpRoot);
            SnipMigrator snipMigrator = new SnipMigrator(context);
            snipMigrator.setMigrateAttachments(false);
            snipMigrator.migrate(snipSnapDumpRoot);

            new NewsMigrator(context).migrate(snipSnapDumpRoot);

//            //should be run after the user migration
            new GroupFileMigrator(context).migrate(snipSnapDumpRoot);

            //alwasy shoud be the last step
            new PackageFileMigrator(context).migrate(snipSnapDumpRoot);

            for (String macro : MacroListTransformation.macros) {
                System.out.println(macro);
            }
        } catch (JDOMException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new App(new File(args.length < 1 ? "/home/elek/jhacks-2.snip" : args[0]), new File(args.length < 2 ? "/tmp/xwiki" : args[1])).migrate();

    }
}
