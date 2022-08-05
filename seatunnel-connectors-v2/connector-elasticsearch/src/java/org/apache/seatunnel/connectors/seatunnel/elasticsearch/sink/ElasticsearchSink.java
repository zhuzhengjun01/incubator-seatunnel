/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.seatunnel.connectors.seatunnel.elasticsearch.sink;

import com.google.auto.service.AutoService;
import org.apache.seatunnel.api.common.PrepareFailException;
import org.apache.seatunnel.api.common.SeaTunnelContext;
import org.apache.seatunnel.api.sink.SeaTunnelSink;
import org.apache.seatunnel.api.sink.SinkWriter;
import org.apache.seatunnel.api.table.type.SeaTunnelDataType;
import org.apache.seatunnel.api.table.type.SeaTunnelRow;
import org.apache.seatunnel.api.table.type.SeaTunnelRowType;
import org.apache.seatunnel.connectors.seatunnel.elasticsearch.config.SinkConfig;
import org.apache.seatunnel.connectors.seatunnel.elasticsearch.state.ElasticsearchAggregatedCommitInfo;
import org.apache.seatunnel.connectors.seatunnel.elasticsearch.state.ElasticsearchCommitInfo;
import org.apache.seatunnel.connectors.seatunnel.elasticsearch.state.ElasticsearchSinkState;

import java.util.Collections;


@AutoService(SeaTunnelSink.class)
public class ElasticsearchSink implements SeaTunnelSink<SeaTunnelRow, ElasticsearchSinkState, ElasticsearchCommitInfo, ElasticsearchAggregatedCommitInfo> {


    private org.apache.seatunnel.shade.com.typesafe.config.Config pluginConfig;
    private SeaTunnelContext seaTunnelContext;
    private SeaTunnelRowType seaTunnelRowType;


    @Override
    public String getPluginName() {
        return "Elasticsearch";
    }

    @Override
    public void prepare(org.apache.seatunnel.shade.com.typesafe.config.Config pluginConfig) throws PrepareFailException {
        this.pluginConfig = pluginConfig;
        SinkConfig.setValue(pluginConfig);
    }

    @Override
    public void setTypeInfo(SeaTunnelRowType seaTunnelRowType) {
        this.seaTunnelRowType = seaTunnelRowType;
    }

    @Override
    public SeaTunnelDataType<SeaTunnelRow> getConsumedType() {
        return this.seaTunnelRowType;
    }

    @Override
    public SinkWriter<SeaTunnelRow, ElasticsearchCommitInfo, ElasticsearchSinkState> createWriter(SinkWriter.Context context) {
        return new ElasticsearchSinkWriter(context, seaTunnelRowType, pluginConfig, Collections.emptyList());
    }

    @Override
    public void setSeaTunnelContext(SeaTunnelContext seaTunnelContext) {
        this.seaTunnelContext = seaTunnelContext;
    }

}