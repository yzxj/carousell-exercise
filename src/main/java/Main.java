import ratpack.exec.Blocking;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.groovy.template.TextTemplateModule;
import ratpack.guice.Guice;

import ratpack.jackson.Jackson;
import static ratpack.groovy.Groovy.groovyTemplate;
import static ratpack.jackson.Jackson.json;
import static ratpack.jackson.Jackson.jsonNode;

import java.util.*;
import java.util.Date;
import java.sql.*;

import com.heroku.sdk.jdbc.DatabaseUrl;

public class Main {
  public static void main(String... args) throws Exception {
    RatpackServer.start(s -> s
        .serverConfig(c -> c
          .baseDir(BaseDir.find())
          .env())

        .registry(Guice.registry(b -> b
          .module(TextTemplateModule.class, conf -> conf.setStaticallyCompile(true))))

        .handlers(chain -> chain
            .get(ctx -> ctx.render(groovyTemplate("index.html")))

            .get("test", ctx -> {
            	TopicManager tm = TopicManager.getInstance();
            	Topic tester = new Topic("test");
            	tm.addTopic(tester);
            	tm.addTopic("1");
            	tm.addTopic("2");
            	tm.addTopic("3");
            	tm.addTopic("4");
            	tm.addTopic("5");
            	tm.addTopic("6");
            	tm.addTopic("7");
            	tm.addTopic("8");
            	tm.addTopic("9");
            	tm.addTopic("10");
            	tm.addTopic("11");
            	tm.addTopic("12");
            	tm.addTopic("13");
            	tm.addTopic("14");
            	tm.addTopic("15");
            	tm.addTopic("16");
            	tm.addTopic("17");
            	tm.addTopic("18");
            	tm.addTopic("19");
            	tm.addTopic("20");
            	tester.voteUp();
            	ctx.render(TopicManager.getInstance().getTopics().toString());
            	tm.reset();
        	})
            
            .get("api/topics", ctx -> {
            	TopicManager tm = TopicManager.getInstance();
            	ctx.render(json(tm.getTopics()));
            })
            .post("api/newtopic", ctx -> {
            	TopicManager tm = TopicManager.getInstance();
            	ctx.parse(jsonNode()).then(n -> {
            		tm.addTopic(n.get("content").asText());
            		ctx.render(json(tm.getTopics())); }
            	);
            })
            .post("api/upvote", ctx -> {
            	TopicManager tm = TopicManager.getInstance();
            	ctx.parse(jsonNode()).then(n -> {
            		String content = n.get("content").asText();
            		int upvotes = n.get("upvotes").asInt();
            		Date timeCreated = new Date(n.get("timeCreated").asLong());
            		Topic topicToVote = new Topic(content, upvotes, timeCreated);
            		tm.upvoteTopic(topicToVote);
            		ctx.render(json(tm.getTopics())); }
            	);
            })
            .post("api/downvote", ctx -> {
            	TopicManager tm = TopicManager.getInstance();
            	ctx.parse(jsonNode()).then(n -> {
            		String content = n.get("content").asText();
            		int upvotes = n.get("upvotes").asInt();
            		Date timeCreated = new Date(n.get("timeCreated").asLong());
            		Topic topicToVote = new Topic(content, upvotes, timeCreated);
            		tm.downvoteTopic(topicToVote);
            		ctx.render(json(tm.getTopics())); }
            	);
            })

            .get("db", ctx -> {
              boolean local = !"cedar-14".equals(System.getenv("STACK"));

              Blocking.get(() -> {
                Connection connection = null;

                try {
                  connection = DatabaseUrl.extract(local).getConnection();
                  Statement stmt = connection.createStatement();
                  stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
                  stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
                  return stmt.executeQuery("SELECT tick FROM ticks");
                } finally {
                  if (connection != null) try {
                    connection.close();
                  } catch (SQLException e) {
                  }
                }
              }).onError(throwable -> {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("message", "There was an error: " + throwable);
                ctx.render(groovyTemplate(attributes, "error.html"));
              }).then(rs -> {
                ArrayList<String> output = new ArrayList<>();
                while (rs.next()) {
                  output.add("Read from DB: " + rs.getTimestamp("tick"));
                }

                Map<String, Object> attributes = new HashMap<>();
                attributes.put("results", output);
                ctx.render(groovyTemplate(attributes, "db.html"));
              });
            })

            .files(f -> f.dir("public"))
        )
    );
  }
}