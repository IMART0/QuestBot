package am.martirosyan.adminapi.mapper;

public interface Mapper<Entity, Request, Response> {

    Entity toEntity(Request request);

    Response toResponse(Entity entity);
}
