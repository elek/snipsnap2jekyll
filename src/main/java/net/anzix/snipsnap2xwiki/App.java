package net.anzix.snipsnap2xwiki;

import net.anzix.snipsnap2xwiki.migrator.SnipMigrator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.File;
import java.io.IOException;

/**
 * SnipSnap2Jekyll migration spaghetti edition.
 *
 */
public class App {

    @Argument(index = 0, metaVar = "dump_file", usage = "SnipSnap wiki dump file", required = true)
    private File oldFile;

    @Argument(index = 1, metaVar = "dest_dir", usage = "Destination directory", required = true)
    private File newDir;

    public void migrate() throws Exception {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document d = builder.build(oldFile);
//            Document d = builder.build(new InputStreamReader(new InputStream() {
//                int i=0;
//                @Override
//                public int read() throws IOException {
//                    if (i++ % 1024 == 0){
//                        //System.out.println(i/1024);
//                    }
//                    int ch = st.read();
//                    System.out.println(ch +" "+(char)ch);
//                    if ((ch & 0xC0) == 0xA0) {
//                        System.out.println("a");
//                    }
//                    return ch;
//                }
//
//            }));
            Element snipSnapDumpRoot = d.getRootElement();

            MigrationContext context = new MigrationContext(newDir);
            context.init(snipSnapDumpRoot);

            //Migrate wiki pages
            SnipMigrator snipMigrator = new SnipMigrator(context);
            snipMigrator.setMigrateAttachments(false);
            snipMigrator.migrate(snipSnapDumpRoot);

            //new NewsMigrator(context).migrate(snipSnapDumpRoot);

//            //should be run after the user migration
            //new GroupFileMigrator(context).migrate(snipSnapDumpRoot);

            //alwasy shoud be the last step
            //new PackageFileMigrator(context).migrate(snipSnapDumpRoot);

        } catch (JDOMException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        App dump = new App();
        CmdLineParser parser = new CmdLineParser(dump);
        try {
            parser.parseArgument(args);
            dump.migrate();
        } catch (CmdLineException ex) {
            System.out.println("ERROR: " + ex.getMessage() + "\n");
            parser.printSingleLineUsage(System.out);
            System.out.println("\n\n");
            parser.printUsage(System.out);
        }

    }
}
