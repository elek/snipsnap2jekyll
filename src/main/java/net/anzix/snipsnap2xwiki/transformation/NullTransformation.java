/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

/**
 * @author elek
 */
public class NullTransformation implements Transformation {

    @Override
    public String transform(String source, Page page) {
        return source;
    }

}
