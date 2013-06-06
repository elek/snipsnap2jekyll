package net.anzix.snipsnap2xwiki.migrator;


import net.anzix.snipsnap2xwiki.Page;

import java.util.List;

/**
 * Interface to save the page to a specific location.
 */
public interface Outputter {
    public void write(Page page);
}

