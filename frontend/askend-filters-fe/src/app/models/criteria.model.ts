export type FilterType = 'AMOUNT' | 'TITLE' | 'DATE';

export enum AmountCondition {
  EQUAL = 'EQUALS',
  LESS_THAN = 'LESS_THAN',
  GREATER_THAN = 'GREATER_THAN',
  GREATER_OR_EQUAL = 'GREATER_OR_EQUAL',
  LESS_OR_EQUAL = 'LESS_OR_EQUAL'
}

export enum TitleCondition {
  CONTAINS = 'CONTAINS',
  EQUALS = 'EQUALS',
  STARTS_WITH = 'STARTS_WITH',
  ENDS_WITH = 'ENDS_WITH'
}

export enum DateCondition {
  BEFORE = 'BEFORE',
  AFTER = 'AFTER',
  EQUALS = 'EQUALS'
}
export interface AmountCriteria {
  filterType: 'AMOUNT';
  condition: AmountCondition;
  value: number;
}

export interface TitleCriteria {
  filterType: 'TITLE';
  condition: TitleCondition;
  value: string;
}

export interface DateCriteria {
  filterType: 'DATE';
  condition: DateCondition;
  value: string;
}

export type Criteria = AmountCriteria | TitleCriteria | DateCriteria;
