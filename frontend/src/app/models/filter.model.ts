import { Criteria } from './criteria.model';

export interface Filter {
  id?: number;
  name: string;
  criteria: Criteria[];
}
