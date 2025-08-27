import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Filter } from '../models/filter.model';

@Injectable({ providedIn: 'root' })
export class FilterService {
  private baseUrl = 'http://localhost:8080/api/filters';

  constructor(private http: HttpClient) {}

  getFilters(): Observable<Filter[]> {
    return this.http.get<Filter[]>(this.baseUrl);
  }

  addFilter(filter: Filter): Observable<Filter> {
    return this.http.post<Filter>(this.baseUrl, filter);
  }

  updateFilter(filter: Filter): Observable<Filter> {
    return this.http.put<Filter>(`${this.baseUrl}/${filter.id}`, filter);
  }

  deleteFilter(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
