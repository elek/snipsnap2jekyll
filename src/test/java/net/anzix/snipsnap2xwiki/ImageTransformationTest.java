package net.anzix.snipsnap2xwiki;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ImageTransformationTest extends TestCase {

	public void testTransform() {
		ImageTransformation transformation = new ImageTransformation();
		Assert.assertEquals("{image:hirig.gif}", transformation.transform("{image:img=WTF/hirig.gif}"));
		Assert.assertEquals("{image:buaaa.gif}", transformation.transform("{image:img=buaaa.gif}"));
	}

}
