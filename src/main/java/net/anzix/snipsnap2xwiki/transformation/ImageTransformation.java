package net.anzix.snipsnap2xwiki.transformation;

/**
 * This class transforms images from format
 * <pre>
 * {image:img=WTF/hirig.gif}
 * </pre>
 * to <pre>
 * {image:hirig.gif}
 * </pre>
 * @author kocka
 *
 */
public class ImageTransformation implements Transformation {

	@Override
	public String transform(String source) {
		return source.replaceAll("\\{image:img=([a-zA-Z]*/)*", "{image:");
	}

}
