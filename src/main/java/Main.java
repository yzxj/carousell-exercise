import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.groovy.template.TextTemplateModule;
import ratpack.guice.Guice;

import static ratpack.groovy.Groovy.groovyTemplate;
import static ratpack.jackson.Jackson.json;
import static ratpack.jackson.Jackson.jsonNode;

import java.util.Date;

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
            
            .get("api/topics", ctx -> {
            	TopicManager tm = TopicManager.getInstance();
            	ctx.render(json(tm.getTopics()));
            })
            .post("api/newtopic", ctx -> {
            	TopicManager tm = TopicManager.getInstance();
            	// This convoluted statement parses the POST data into variable n.
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

            .files(f -> f.dir("public"))
        )
    );
  }
}