/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

/**
 * Transform the content of a wiki page.
 *
 * @author elek
 */
public interface Transformation {

    public String transform(String source, Page page);
}
