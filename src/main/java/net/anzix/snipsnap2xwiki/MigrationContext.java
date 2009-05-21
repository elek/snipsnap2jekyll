/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.anzix.snipsnap2xwiki.transformation.DefaultTextTransformation;
import net.anzix.snipsnap2xwiki.transformation.Transformation;
import org.jdom.Element;

/**
 *
 * @author elek
 */
public class MigrationContext {

    private Transformation syntaxTransformation;

    private File outputDir = new File("/tmp/xwiki");

    private Map<String, Element> userCache = new HashMap();

    private Map<String, Element> snipCache = new HashMap();

    private Map<String, List<Element>> commentsCache = new HashMap<String, List<Element>>();

    private Map<String, String> snipNameCache = new HashMap();

    /**
     * User names whcih migrated successfully
     */
    private Set<String> usersMigrated = new HashSet();

    /**
     * Page names whcih migrated successfully
     */
    private Set<String> pagesMigrated = new HashSet();

    public void init(Element root) {
        syntaxTransformation = new DefaultTextTransformation(this);
        List<Element> childs = root.getChildren();
        for (Element e : childs) {
            if (e.getName().equals("snip")) {
                String snipName = e.getChildText("name");
                snipNameCache.put(snipName.toLowerCase(), snipName);
                snipCache.put(snipName, e);

                //comments cache
                if (snipName.startsWith("comment-")) {
                    String parentName = snipName.substring("comment-".length());
                    parentName = parentName.substring(0, parentName.lastIndexOf("-"));

                    if (commentsCache.get(parentName) == null) {
                        commentsCache.put(parentName, new ArrayList<Element>());
                    }
                    commentsCache.get(parentName).add(e);


                }

            } else if (e.getName().equals("user")) {
                String login = e.getChildText("login");
                userCache.put(login, e);
            }
        }
    }

    public Map<String, List<Element>> getCommentsCache() {
        return commentsCache;
    }

    public void setCommentsCache(Map<String, List<Element>> commentsCache) {
        this.commentsCache = commentsCache;
    }

    public Map<String, Element> getSnipCache() {
        return snipCache;
    }

    public void setSnipCache(Map<String, Element> snipCache) {
        this.snipCache = snipCache;
    }

    public Map<String, String> getSnipNameCache() {
        return snipNameCache;
    }

    public void setSnipNameCache(Map<String, String> snipNameCache) {
        this.snipNameCache = snipNameCache;
    }

    public Map<String, Element> getUserCache() {
        return userCache;
    }

    public void setUserCache(Map<String, Element> userCache) {
        this.userCache = userCache;
    }

    public void userMigratedSuccesfully(String userName) {
        usersMigrated.add(userName);
    }

    public void pageMigratedSuccesfully(String pageName) {
        pagesMigrated.add(pageName);
    }

    public Set<String> getPagesMigrated() {
        return pagesMigrated;
    }

    public Set<String> getUsersMigrated() {
        return usersMigrated;
    }

    public File getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

    public Transformation getSyntaxTransformation() {
        return syntaxTransformation;
    }
}
