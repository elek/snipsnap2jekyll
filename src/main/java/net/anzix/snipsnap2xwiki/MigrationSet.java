package net.anzix.snipsnap2xwiki;

import net.anzix.snipsnap2xwiki.migrator.MigrationStep;
import net.anzix.snipsnap2xwiki.transformation.NullTransformation;
import net.anzix.snipsnap2xwiki.transformation.Transformation;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * List of DOM transformation during the migration.
 */
public class MigrationSet {

    List<MigrationStep> steps = new ArrayList<MigrationStep>();

    public void copyText(Element root, Page page, String from, String to, Transformation transformation) {
        String string = root.getChildText(from);
        if (to.equals("content")) {
            page.setContent(transformation.transform(string, page));
        } else {
            page.addMeta(to, transformation.transform(string, page));
        }
    }

    public void copyText(Element room, Page page, String from, String to) {
        copyText(room, page, from, to, new NullTransformation());
    }


    public void add(MigrationStep migrationStep) {
        steps.add(migrationStep);
    }

    public void migrate(Element from, Page to) {
        for (MigrationStep step : steps) {
            copyText(from, to, step.from, step.to, step.transformation);
        }
    }
}
