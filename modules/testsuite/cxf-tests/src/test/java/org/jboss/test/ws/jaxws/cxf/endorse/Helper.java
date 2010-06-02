/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.ws.jaxws.cxf.endorse;

import org.apache.cxf.BusFactory;
import org.jboss.wsf.stack.cxf.client.configuration.JBossWSSpringBusFactory;
import org.jboss.wsf.test.JBossWSTestHelper;

/**
 * 
 * @author alessio.soldano@jboss.com
 * @since 02-Jun-2010
 *
 */
public class Helper
{
   public static void verify()
   {
      //check BusFactory customization; this is required by the JBWS-CXF Configurer integration (HTTPConduit customization, JAXBIntros, ...)
      BusFactory factory = BusFactory.newInstance();
      if (!(factory instanceof JBossWSSpringBusFactory))
         throw new RuntimeException("Expected " + JBossWSSpringBusFactory.class + " but got " + (factory == null ? null : factory.getClass()));
      
      //check the Apache CXF JAXWS implementation is actually used
      if (!JBossWSTestHelper.isIntegrationCXF())
         throw new RuntimeException("JAXWS implementation is not properly endorsed!");
   }
}
