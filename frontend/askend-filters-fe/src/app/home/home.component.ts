import { Component, OnInit } from '@angular/core';
import { FilterService } from '../services/filter.service';
import { Filter } from '../models/filter.model';
import { FilterDialogComponent } from '../filter-dialog/filter-dialog.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatCardModule,
    FilterDialogComponent,
    MatFormFieldModule,
    MatSelectModule
  ]
})
export class HomeComponent implements OnInit {
  filters: Filter[] = [];
  selectedFilterId?: number;
  showInlineForm = false;

  constructor(private filterService: FilterService) {}

  ngOnInit(): void {
    this.loadFilters();
  }

  loadFilters() {
    this.filterService.getFilters().subscribe(f => {
      this.filters = f;
      if (this.selectedFilterId && !this.filters.find(x => x.id === this.selectedFilterId)) {
        this.selectedFilterId = undefined;
        this.showInlineForm = false;
      }
    });
  }

  onAddFilter() {
    this.selectedFilterId = undefined;
    this.showInlineForm = true;
  }

  onFilterSelected(id: number) {
    this.selectedFilterId = id;
    this.showInlineForm = true;
  }

  onFilterSaved() {
    this.showInlineForm = false;
    this.selectedFilterId = undefined;
    this.loadFilters();
  }

  onFilterDeleted() {
    this.showInlineForm = false;
    this.selectedFilterId = undefined;
    this.loadFilters();
  }

  onFilterCanceled() {
    this.showInlineForm = false;
    this.selectedFilterId = undefined;
  }
}
