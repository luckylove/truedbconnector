package com.trues.config;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.trues.config.model.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ServiceConfig /*extends AbstractRulesModule */{

    private static Environments  environments;

   /* @Override
    protected void configure()
    {
        forPattern( "resources/environments" ).createObject().ofType( Environments.class ).then()
                .setProperties();
        forPattern( "resources/environments/env").createObject().ofType(Env.class).then().setProperties().then().setNext("addEnv");

        forPattern("resources/environments/env/getConfig").createObject().ofType(GetConfig.class).then().setNext("setGetConfig");
        forPattern("resources/environments/env/getConfig/dataSource").createObject().ofType(DataSource.class).then().setNext("addDataSource");
        forPattern("resources/environments/env/getConfig/dataSource/driver").setBeanProperty();
        forPattern("resources/environments/env/getConfig/dataSource/url").setBeanProperty();
        forPattern("resources/environments/env/getConfig/dataSource/username").setBeanProperty();
        forPattern("resources/environments/env/getConfig/dataSource/password").setBeanProperty();

        forPattern("resources/environments/env/getConfig/dataSource/connectionProperties").setBeanProperty();
        forPattern("resources/environments/env/getConfig/dataSource/removeAbandoned").setBeanProperty();
        forPattern("resources/environments/env/getConfig/dataSource/removeAbandonedTimeout").setBeanProperty();
        forPattern("resources/environments/env/getConfig/dataSource/maxActive").setBeanProperty();
        forPattern("resources/environments/env/getConfig/dataSource/maxIdle").setBeanProperty();
        forPattern("resources/environments/env/getConfig/dataSource/maxWait").setBeanProperty();
        forPattern("resources/environments/env/getConfig/dataSource/testWhileIdle").setBeanProperty();
        forPattern("resources/environments/env/getConfig/dataSource/timeBetweenEvictionRunsMillis").setBeanProperty();
        forPattern("resources/environments/env/getConfig/dataSource/minEvictableIdleTimeMillis").setBeanProperty();
        forPattern("resources/environments/env/getConfig/dataSource/testOnBorrow").setBeanProperty();
        forPattern("resources/environments/env/getConfig/dataSource/validationQuery").setBeanProperty();

        forPattern("resources/environments/env/getConfig/dataSource/configs/config").createObject().ofType(Config.class).then().setProperties().then().setBeanProperty().then()
                .setNext("addConfig");

    }*/

    public static Environments parse(InputStream inputStream) throws IOException, SAXException {

        XStream xstream = new XStream(new StaxDriver());
        xstream.ignoreUnknownElements();
        xstream.processAnnotations(Config.class);
        xstream.alias("resources", Resources.class);
        xstream.alias("environments", Environments.class);
        xstream.useAttributeFor(Environments.class, "active");
        xstream.addImplicitMap(Environments.class, "envs", "env", Env.class, "name");
        xstream.useAttributeFor(Env.class, "name");
        xstream.alias("getConfig", GetConfig.class);
        xstream.addImplicitCollection(GetConfig.class, "dataSources", "dataSource", DataSource.class);
        xstream.alias("configs", List.class);
        xstream.alias("config", Config.class);
        xstream.useAttributeFor(Config.class, "method");
        Resources environments = (Resources)xstream.fromXML(inputStream);
        ServiceConfig.environments = environments.getEnvironments();

       /* DigesterLoader loader = newLoader( new ServiceConfig() )
                .setNamespaceAware( true )
                .setXIncludeAware( true );
        Digester digester = loader.newDigester();
        ServiceConfig.environments = digester.parse(inputStream);*/
        return  ServiceConfig.environments;
    }
}
