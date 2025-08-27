import { Criteria, AmountCriteria, TitleCriteria, DateCriteria } from './criteria.model';
import { AmountCondition } from './criteria.model';

describe('Criteria', () => {
  it('should create a valid AmountCriteria object', () => {
    const criteria: AmountCriteria = {
      filterType: 'AMOUNT',
      condition: AmountCondition.GREATER_OR_EQUAL,
      value: 100
    };
    expect(criteria).toBeTruthy();
  });
});
