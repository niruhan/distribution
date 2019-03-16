/*
 *   Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */

package io.siddhi.distribution.event.simulator.core.api.api;

import io.siddhi.distribution.event.simulator.core.exception.exception.FileOperationsException;
import io.siddhi.distribution.event.simulator.core.factories.factories.FilesApiServiceFactory;
import io.siddhi.distribution.event.simulator.core.internal.util.util.EventSimulatorConstants;
import io.siddhi.distribution.event.simulator.core.model.model.InlineResponse2001;
import io.siddhi.distribution.event.simulator.core.service.service.EventSimulatorDataHolder;
import io.siddhi.distribution.event.simulator.core.service.service.EventSimulatorMap;
import io.siddhi.distribution.msf4j.interceptor.common.common.AuthenticationInterceptor;
import io.swagger.annotations.ApiParam;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.utils.Utils;
import org.wso2.msf4j.Microservice;
import org.wso2.msf4j.Request;
import org.wso2.msf4j.formparam.FileInfo;
import org.wso2.msf4j.formparam.FormDataParam;
import org.wso2.msf4j.interceptor.annotation.RequestInterceptor;

import java.io.InputStream;
import java.nio.file.Paths;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * File API.
 */
@Component(
        name = "simulator-core-file-services",
        service = Microservice.class,
        immediate = true
)
@Path("/simulation/files")
@RequestInterceptor(AuthenticationInterceptor.class)
@io.swagger.annotations.Api(description = "the files API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaMSF4JServerCodegen",
        date = "2017-07-20T09:30:14.336Z")
public class FilesApi implements Microservice {
    private static final Logger log = LoggerFactory.getLogger(FilesApi.class);
    private final FilesApiService delegate = FilesApiServiceFactory.getFilesApi();

    @DELETE
    @Path("/{fileName}")
    @Produces({"application/json"})
    @io.swagger.annotations.ApiOperation(value = "Update CSV file to simulate event flow", notes = "",
            response = void.class, tags = {"simulator"})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully deleted the csv file",
                    response = void.class),

            @io.swagger.annotations.ApiResponse(code = 404,
                    message = "No event simulation configuration available under "
                            + "simulation name",
                    response = void.class)})
    public Response deleteFile(
            @Context Request request,
            @ApiParam(value = "CSV File for name to delete", required = true)
            @PathParam("fileName") String fileName) throws NotFoundException, FileOperationsException {
        return delegate.deleteFile(fileName, request);
    }

    @GET
    @Produces({"application/json"})
    @io.swagger.annotations.ApiOperation(value = "Get CSV file names", notes = "", response = InlineResponse2001.class,
            tags = {"simulator"})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully retrieved file names",
                    response = InlineResponse2001.class),
            @io.swagger.annotations.ApiResponse(code = 404,
                    message = "No event simulation configuration available under "
                            + "simulation name",
                    response = InlineResponse2001.class)})
    public Response getFileNames(@Context Request request) throws NotFoundException {
        return delegate.getFileNames(request);
    }

    @PUT
    @Path("/{fileName}")
    @Consumes({"multipart/form-data"})
    @Produces({"application/json"})
    @io.swagger.annotations.ApiOperation(value = "Update CSV file to simulate event flow", notes = "",
            response = void.class, tags = {"simulator"})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully updated the csv file",
                    response = void.class),

            @io.swagger.annotations.ApiResponse(code = 404,
                    message = "No event simulation configuration available under "
                            + "simulation name",
                    response = void.class)})
    public Response updateFile(
            @Context Request request,
            @ApiParam(value = "CSV File for name to update", required = true)
            @PathParam("fileName") String fileName,
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FileInfo fileDetail)
            throws NotFoundException, FileOperationsException {
        return delegate.updateFile(fileName, fileInputStream, fileDetail, request);
    }

    @POST
    @Consumes({"multipart/form-data"})
    @Produces({"application/json"})
    @io.swagger.annotations.ApiOperation(value = "Upload CSV file to simulate event flow", notes = "",
            response = void.class, tags = {"simulator"})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully uploaded file",
                    response = void.class),

            @io.swagger.annotations.ApiResponse(code = 404,
                    message = "No event simulation configuration available under simulation name",
                    response = void.class)})
    public Response uploadFile(
            @Context Request request,
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FileInfo fileDetail) throws NotFoundException, FileOperationsException {
        return delegate.uploadFile(fileInputStream, fileDetail, request);
    }

    /**
     * This is the activation method of ServiceComponent. This will be called when it's references are fulfilled.
     *
     * @throws Exception this will be thrown if an issue occurs while executing the activate method
     */
    @Activate
    protected void start() throws Exception {
        //set maximum csv file size to 8MB
        EventSimulatorDataHolder.getInstance().setMaximumFileSize(8388608);
        EventSimulatorDataHolder.getInstance().setCsvFileDirectory(Paths.get(Utils.getRuntimePath().toString(),
                EventSimulatorConstants.DIRECTORY_DEPLOYMENT, EventSimulatorConstants.DIRECTORY_CSV_FILES).toString());
        if (log.isDebugEnabled()) {
            log.debug("Event Simulator file service component is activated");
        }
    }

    /**
     * This is the deactivation method of ServiceComponent. This will be called when this component
     * is being stopped or references are satisfied during runtime.
     *
     * @throws Exception this will be thrown if an issue occurs while executing the de-activate method
     */
    @Deactivate
    protected void stop() throws Exception {
        EventSimulatorMap.getInstance().stopAllActiveSimulations();
        log.info("Simulator service file component is deactivated");
    }
}
