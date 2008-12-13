package net.anzix.snipsnap2xwiki;

public class CompositeTransformation implements Transformation {

	CompositeTransformation(Transformation[] transformations) {
		this.transformations = transformations;
	}

	Transformation[] transformations;

	@Override
	public String transform(String source) {
		String ret = source;
		for(Transformation transformation : transformations) {
			ret = transformation.transform(ret);;
		}
		return ret;
	}

}