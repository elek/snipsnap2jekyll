/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.migator;

import net.anzix.snipsnap2xwiki.*;
import net.anzix.snipsnap2xwiki.migator.AbstractObjectMigrator;
import java.io.File;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author elek
 */
public class UserMigrator extends AbstractObjectMigrator {

    public UserMigrator(MigrationContext context) {
        super(context);
    }

    @Override
    protected boolean includeInMigration(Element e) {
        return getContext().getUserCache().containsKey(e.getChildText("login")) && e.getName().equals("user");
    }

    @Override
    public void migrateObject(Element userRoot) throws Exception {
        String name = userRoot.getChildText("login");

        SAXBuilder builder = new SAXBuilder();
        Document d = builder.build(new File("src/main/template/User.xml"));
        //open template
        Element newRoot = d.getRootElement();
        //copy properties
        DomCopier copier = new DomCopier(userRoot, newRoot);
        copier.copyText("login", "name");

        copier.copyText("cTime", "creationDate");
        copier.copyText("mTime", "date");

        fixObjextNames(name, newRoot);

        //add rights to modify own profile
        modifyProperty(findLastObject(newRoot, "XWiki.XWikiRights"), "users", "XWiki." + name);
        //correct first_name property
        modifyProperty(findFirstObject(newRoot, "XWiki.XWikiUsers"), "first_name", name);
        modifyProperty(findFirstObject(newRoot, "XWiki.XWikiUsers"), "last_name", "");
        modifyProperty(findFirstObject(newRoot, "XWiki.XWikiUsers"), "email", userRoot.getChildText("email"));
        modifyProperty(findFirstObject(newRoot, "XWiki.XWikiUsers"), "password", "hash:SHA1:" + userRoot.getChildText("passwd"));

        writeFile("XWiki", name, d);

        getContext().userMigratedSuccesfully(name);

    }
}
