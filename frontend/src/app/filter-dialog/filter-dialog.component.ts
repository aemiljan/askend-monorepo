import {Component, EventEmitter, Input, OnChanges, Output, QueryList, SimpleChanges, ViewChildren} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatDatepicker, MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';

import {FilterService} from '../services/filter.service';
import {Filter} from '../models/filter.model';
import {AmountCondition, Criteria, DateCondition, FilterType, TitleCondition} from '../models/criteria.model';
import {DeleteConfirmDialog} from './delete-confirm-dialog/delete-confirm-dialog';

@Component({
  selector: 'app-filter-dialog',
  templateUrl: './filter-dialog.component.html',
  styleUrls: ['./filter-dialog.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule
  ]
})
export class FilterDialogComponent implements OnChanges {
  @ViewChildren('picker') pickerArray!: QueryList<MatDatepicker<any>>;
  @Input() filterId?: number;
  @Input() nonModal = false;
  @Output() saved = new EventEmitter<void>();
  @Output() canceled = new EventEmitter<void>();
  @Output() deleted = new EventEmitter<void>();

  form: FormGroup;
  currentFilterId?: number;

  constructor(
    private fb: FormBuilder,
    private filterService: FilterService,
    private dialog: MatDialog
  ) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      criteria: this.fb.array([])
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['filterId']) {
      if (this.filterId) {
        this.filterService.getFilters().subscribe(list => {
          const found = list.find(x => x.id === this.filterId);
          this.loadFilter(found);
        });
      } else {
        this.loadFilter(undefined);
      }
    }
  }

  get criteria(): FormArray {
    return this.form.get('criteria') as FormArray;
  }

  createCriteria(c?: Criteria): FormGroup {
    return this.fb.group({
      filterType: [c?.filterType || 'AMOUNT', Validators.required],
      condition: [c?.condition || '', Validators.required],
      value: [c?.value || '', Validators.required]
    });
  }

  addCriteria(c?: Criteria) {
    this.criteria.push(this.createCriteria(c));
  }

  removeCriteria(index: number) {
    if (this.criteria.length > 1) this.criteria.removeAt(index);
  }

  loadFilter(filter?: Filter) {
    this.form.reset();
    this.criteria.clear();
    if (filter) {
      this.currentFilterId = filter.id;
      this.form.get('name')?.setValue(filter.name);
      filter.criteria.forEach(c => this.addCriteria(c));
    } else {
      this.currentFilterId = undefined;
      this.addCriteria();
    }
  }

  getConditions(type: FilterType): any[] {
    switch (type) {
      case 'AMOUNT':
        return Object.values(AmountCondition);
      case 'TITLE':
        return Object.values(TitleCondition);
      case 'DATE':
        return Object.values(DateCondition);
      default:
        return [];
    }
  }

  save() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload: Filter = {
      id: this.currentFilterId,
      name: this.form.value.name,
      criteria: this.form.value.criteria
    };

    if (payload.id) {
      this.filterService.updateFilter(payload).subscribe({
        next: () => this.saved.emit(),
        error: (err) => {
          if (err.status === 400) {
            alert('Filter is not properly filled. Please check your fields.');
          }
        }
      });
    } else {
      this.filterService.addFilter(payload).subscribe({
        next: () => this.saved.emit(),
        error: (err) => {
          if (err.status === 400) {
            alert('Filter is not properly filled. Please check your fields.');
          }
        }
      });
    }
  }

  confirmDelete() {
    if (!this.currentFilterId) return;
    const ref = this.dialog.open(DeleteConfirmDialog);
    ref.afterClosed().subscribe(ok => {
      if (ok) {
        this.filterService.deleteFilter(this.currentFilterId!).subscribe(() => this.deleted.emit());
      }
    });
  }

  cancel() {
    this.canceled.emit();
  }
}
