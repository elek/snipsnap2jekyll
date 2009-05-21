package net.anzix.snipsnap2xwiki.transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeTransformation implements Transformation {

    protected List<Transformation> transformations;

    public CompositeTransformation(Transformation[] transformations) {
        this.transformations = Arrays.asList(transformations);
    }

    public CompositeTransformation() {
        transformations = new ArrayList();
    }

    @Override
    public String transform(String source) {
        String ret = source;
        for (Transformation transformation : transformations) {
            ret = transformation.transform(ret);
        }
        return ret;
    }
}
