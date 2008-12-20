/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.transformation;

/**
 *
 * @author elek
 */
public class ReplaceTransformation implements Transformation {

    private String replacementValue;

    public ReplaceTransformation(String replacementValue) {
        this.replacementValue = replacementValue;
    }

    @Override
    public String transform(String source) {
        return replacementValue;
    }
}
