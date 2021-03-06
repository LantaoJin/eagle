/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.eagle.jpm.analyzer.resource;

import com.google.inject.Inject;
import org.apache.eagle.common.rest.RESTResponse;
import org.apache.eagle.jpm.analyzer.meta.MetaManagementService;
import org.apache.eagle.jpm.analyzer.meta.model.JobMetaEntity;
import org.apache.eagle.jpm.analyzer.meta.model.PublisherEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.apache.eagle.jpm.analyzer.util.Constants.*;

@Path(ANALYZER_PATH)
public class AnalyzerResource {
    @Inject
    MetaManagementService metaManagementService;

    public AnalyzerResource() {
    }

    @POST
    @Path(META_PATH)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RESTResponse<Void> addJobMeta(JobMetaEntity jobMetaEntity) {
        return RESTResponse.<Void>async((response) -> {
            jobMetaEntity.ensureDefault();
            boolean ret = metaManagementService.addJobMeta(jobMetaEntity);
            String message = "Successfully add job meta for " + jobMetaEntity.getJobDefId();
            if (!ret) {
                message = "Failed to add job meta for " + jobMetaEntity.getJobDefId();
            }
            response.success(ret).message(message);
        }).get();
    }

    @POST
    @Path(JOB_META_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    public RESTResponse<Void> updateJobMeta(@PathParam(JOB_DEF_PATH) String jobDefId, JobMetaEntity jobMetaEntity) {
        return RESTResponse.<Void>async((response) -> {
            jobMetaEntity.ensureDefault();
            boolean ret = metaManagementService.updateJobMeta(jobDefId, jobMetaEntity);
            String message = "Successfully update job meta for " + jobDefId;
            if (!ret) {
                message = "Failed to update job meta for " + jobDefId;
            }
            response.success(ret).message(message);
        }).get();
    }

    @GET
    @Path(JOB_META_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    public RESTResponse<List<JobMetaEntity>> getJobMeta(@PathParam(JOB_DEF_PATH) String jobDefId) {
        return RESTResponse.async(() -> metaManagementService.getJobMeta(jobDefId)).get();
    }

    @DELETE
    @Path(JOB_META_PATH)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RESTResponse<Void> deleteJobMeta(@PathParam(JOB_DEF_PATH) String jobDefId) {
        return RESTResponse.<Void>async((response) -> {
            boolean ret = metaManagementService.deleteJobMeta(jobDefId);
            String message = "Successfully delete job meta for " + jobDefId;
            if (!ret) {
                message = "Failed to delete job meta for " + jobDefId;
            }

            response.success(ret).message(message);
        }).get();
    }

    @POST
    @Path(PUBLISHER_PATH)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RESTResponse<Void> addPublisherMeta(PublisherEntity publisherEntity) {
        return RESTResponse.<Void>async((response) -> {
            publisherEntity.ensureDefault();
            boolean ret = metaManagementService.addPublisherMeta(publisherEntity);
            String message = "Successfully add publisher meta for " + publisherEntity.getUserId();
            if (!ret) {
                message = "Failed to add publisher meta for " + publisherEntity.getUserId();
            }
            response.success(ret).message(message);
        }).get();
    }

    @DELETE
    @Path(PUBLISHER_META_PATH)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RESTResponse<Void> deletePublisherMeta(@PathParam(USER_PATH) String userId) {
        return RESTResponse.<Void>async((response) -> {
            boolean ret = metaManagementService.deletePublisherMeta(userId);
            String message = "Successfully delete publisher meta for " + userId;
            if (!ret) {
                message = "Failed to delete publisher meta for " + userId;
            }
            response.success(ret).message(message);
        }).get();
    }

    @GET
    @Path(PUBLISHER_META_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    public RESTResponse<List<PublisherEntity>> getPublisherMeta(@PathParam(USER_PATH) String userId) {
        return RESTResponse.async(() -> metaManagementService.getPublisherMeta(userId)).get();
    }
}
