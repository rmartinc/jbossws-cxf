/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.test.ws.jaxws.namespace;

import jakarta.jws.HandlerChain;
import jakarta.jws.WebService;

import org.jboss.logging.Logger;

/**
 * Test namespace differences at service and portType levels
 *
 * @author Thomas.Diesler@jboss.org
 * @since 29-Apr-2005
 */
@WebService(endpointInterface = "org.jboss.test.ws.jaxws.namespace.EndpointInterface", targetNamespace = "http://example.org/impl")
@HandlerChain(file="handler-chain.xml")
public class EndpointBean implements EndpointInterface
{
   private static final Logger log = Logger.getLogger(EndpointBean.class);

   public String echo(String message)
   {
      log.info("echo:" + message);
      //message check performed in the configured handler
      return message;
   }
}
