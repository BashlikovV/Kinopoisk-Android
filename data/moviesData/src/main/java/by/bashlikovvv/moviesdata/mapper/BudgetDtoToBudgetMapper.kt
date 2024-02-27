package by.bashlikovvv.moviesdata.mapper

import by.bashlikovvv.core.domain.model.Budget
import by.bashlikovvv.core.domain.model.IMapper
import by.bashlikovvv.moviesdata.remote.response.BudgetDto

class BudgetDtoToBudgetMapper : IMapper<BudgetDto, Budget> {
    override fun mapFromEntity(entity: BudgetDto): Budget {
        return Budget(
            currency = entity.currency,
            value = entity.value
        )
    }

    override fun mapToEntity(domain: Budget): BudgetDto {
        return BudgetDto(
            currency = domain.currency,
            value = domain.value
        )
    }
}