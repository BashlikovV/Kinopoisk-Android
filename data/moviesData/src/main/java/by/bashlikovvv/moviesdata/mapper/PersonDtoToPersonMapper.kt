package by.bashlikovvv.moviesdata.mapper

import by.bashlikovvv.core.domain.model.IMapper
import by.bashlikovvv.core.domain.model.Person
import by.bashlikovvv.moviesdata.remote.response.PersonsItemDto

class PersonDtoToPersonMapper : IMapper<PersonsItemDto, Person> {
    override fun mapFromEntity(entity: PersonsItemDto): Person {
        return Person(
            profession = entity.profession,
            name = entity.name,
            enName = entity.enName,
            photo = entity.photo,
            description = entity.description,
            id = entity.id,
            enProfession = entity.enProfession
        )
    }

    override fun mapToEntity(domain: Person): PersonsItemDto {
        return PersonsItemDto(
            profession = domain.profession,
            name = domain.name,
            enName = domain.enName,
            photo = domain.photo,
            description = domain.description,
            id = domain.id,
            enProfession = domain.enProfession
        )
    }
}