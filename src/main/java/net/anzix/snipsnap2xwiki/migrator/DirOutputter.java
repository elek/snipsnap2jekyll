package net.anzix.snipsnap2xwiki.migrator;

import net.anzix.snipsnap2xwiki.MigrationContext;
import net.anzix.snipsnap2xwiki.Page;

import java.io.File;

/**
 * Class to save the migrated posts to a plain dir structure.
 */
public class DirOutputter implements Outputter {
    private final MigrationContext context;
    File rootDir;

    public DirOutputter(MigrationContext context) {
        this.rootDir = context.getOutputDir();
        this.context = context;

    }

    @Override
    public void write(Page page) {
        writeFile(page);
    }

    public void writeFile(Page page) {
        //write output
        String name = page.getName();
        if (page.getMeta("version") != null) {
            name += ".v" + page.getMeta("version");
        }
        File output = getFile("wiki", name);
        if (!output.getParentFile().exists()) {
            output.getParentFile().mkdirs();
        }
        page.write(output);
        context.pageMigratedSuccesfully(page.getName());

    }

    protected File getFile(Page page) {
        return getFile("wiki", page.getName());
    }

    protected File getFile(String space, String name) {
        return new File(new File(rootDir, space), name + ".md");

    }

}
