/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.http.functional.listener;

import static org.mule.test.http.AllureConstants.HttpFeature.HTTP_EXTENSION;
import org.mule.test.runner.RunnerDelegateTo;

import org.junit.runners.Parameterized;
import io.qameta.allure.Feature;

@RunnerDelegateTo(Parameterized.class)
@Feature(HTTP_EXTENSION)
public class HttpListenerExpectHeaderStreamingAutoStringTestCase extends HttpListenerExpectHeaderStreamingNeverTestCase {

  @Override
  protected String getConfigFile() {
    return "http-listener-expect-header-streaming-auto-string-config.xml";
  }

  public HttpListenerExpectHeaderStreamingAutoStringTestCase(String persistentConnections) {
    super(persistentConnections);
  }

}

