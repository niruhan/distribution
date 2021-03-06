/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.siddhi.distribution.editor.core.util.designview.designgenerator.generators.query.input.types.patternsequencesupporters;

/**
 * Class for count state element configuration.
 */
public class CountStateElementConfig implements StateElementConfig {
    private StreamStateElementConfig streamStateElement;
    private String within;
    private int min;
    private int max;

    public StreamStateElementConfig getStreamStateElement() {
        return streamStateElement;
    }

    public void setStreamStateElement(StreamStateElementConfig streamStateElement) {
        this.streamStateElement = streamStateElement;
    }

    public String getWithin() {
        return within;
    }

    public void setWithin(String within) {
        this.within = within;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
