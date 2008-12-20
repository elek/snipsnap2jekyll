/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.snipsnap2xwiki.transformation;

/**
 *
 * @author elek
 */
public class NullTransformation implements Transformation{

    @Override
    public String transform(String source) {
        return source;
    }

}
