package net.anzix.snipsnap2xwiki.migrator;

import net.anzix.snipsnap2xwiki.transformation.NullTransformation;
import net.anzix.snipsnap2xwiki.transformation.Transformation;

/**
 * Copy one dom element to a field with a tranformation.
 */
public class MigrationStep {
    public String from;
    public String to;
    public Transformation transformation;

    MigrationStep(String from, String to) {
        this.from = from;
        this.to = to;
        this.transformation = new NullTransformation();
    }

    MigrationStep(String from, String to, Transformation transformation) {
        this.from = from;
        this.to = to;
        this.transformation = transformation;
    }
}
