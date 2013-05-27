/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki;

import net.anzix.snipsnap2xwiki.transformation.DefaultTextTransformation;
import net.anzix.snipsnap2xwiki.transformation.Transformation;
import org.jdom.Element;

import java.io.File;
import java.util.*;

/**
 *  Context of the migrations.
 *
 *  Contains list and indexes of the user/snip pages.
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

    private Set<String> missingPages = new HashSet<>();

    /**
     * User names whcih migrated successfully
     */
    private Set<String> usersMigrated = new HashSet();

    /**
     * Page names whcih migrated successfully
     */
    private Set<String> pagesMigrated = new HashSet();

    public MigrationContext(File outputDir) {
        this.outputDir = outputDir;
    }

    public void init(Element root) {
        syntaxTransformation = new DefaultTextTransformation(this);
        List<Element> childs = root.getChildren();
        int i = 0;
        for (Element e : childs) {
            System.out.println(i++);
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
        System.out.println("Sinp cached:" + snipCache.size());
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

    public void addMissingPage(String name) {
        missingPages.add(name);
    }

    public Set<String> getMissingPages() {
        return missingPages;
    }
}
