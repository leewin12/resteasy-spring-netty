package org.greg.resteasy.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLContext;

import org.jboss.netty.bootstrap.Bootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.resteasy.core.SynchronousDispatcher;
import org.jboss.resteasy.plugins.server.netty.HttpServerPipelineFactory;
import org.jboss.resteasy.plugins.server.netty.HttpsServerPipelineFactory;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.plugins.server.netty.RequestDispatcher;

/**
 * exposing bootstrap config
 * 
 * @author Gregory
 * 
 */
public class ConfigurableNettyJaxrsServer extends NettyJaxrsServer {

	private final int			ioWorkerCount		= Runtime.getRuntime().availableProcessors() * 2;
	private final int			executorThreadCount	= 16;
	private SSLContext			sslContext;
	private final int			maxRequestSize		= 1024 * 1024 * 10;
	static final ChannelGroup	allChannels			= new DefaultChannelGroup("NettyJaxrsServer");

	/**
	 * expose bootstrap to be able to config
	 * 
	 * @return
	 */
	public Bootstrap initBootstrap() {
		this.bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool(),
						ioWorkerCount));
		return bootstrap;
	}

	public void setBootstrap(ServerBootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	@Override
	public void start()
	{
		deployment.start();
		RequestDispatcher dispatcher = new RequestDispatcher(
				(SynchronousDispatcher) deployment.getDispatcher(),
				deployment.getProviderFactory(), domain);
		
		// Configure the server.
		if (bootstrap == null) {
			initBootstrap();
		}

		ChannelPipelineFactory factory;
		if (sslContext == null) {
			factory = new HttpServerPipelineFactory(dispatcher, root, executorThreadCount, maxRequestSize);
		} else {
			factory = new HttpsServerPipelineFactory(dispatcher, root, executorThreadCount, maxRequestSize, sslContext);
		}
		// Set up the event pipeline factory.
		bootstrap.setPipelineFactory(factory);

		// Bind and start to accept incoming connections.
		channel = bootstrap.bind(new InetSocketAddress(port));
		allChannels.add(channel);
	}

}
