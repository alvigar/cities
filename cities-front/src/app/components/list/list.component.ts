import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatInputModule } from '@angular/material/input';
import { CitiesService } from 'src/app/services/cities.service';
import { CityDataSource } from 'src/app/helpers/cityDataSource';
import { CommonModule } from '@angular/common'
import { debounceTime, distinctUntilChanged, fromEvent, merge, tap } from 'rxjs';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
  standalone: true,
  imports: [
    MatProgressSpinnerModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    CommonModule,
    MatInputModule, 
  ],
})
export class ListComponent implements AfterViewInit, OnInit {

  displayedColumns: string[] = ['id', 'name'];
  data: CityDataSource;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild('input') input!: ElementRef;

  constructor(private citiesService: CitiesService) {
    this.data = new CityDataSource(this.citiesService);
  }

  ngOnInit() {
    this.data.loadCities(0, 10);
  }

  ngAfterViewInit() {

    fromEvent(this.input.nativeElement, 'keyup')
      .pipe(
        debounceTime(150),
        distinctUntilChanged(),
        tap(() => {
          this.paginator.pageIndex = 0;
          this.data.loadCities(this.paginator.pageIndex, this.paginator.pageSize, this.sort.direction, this.input.nativeElement.value);
        })
      )
      .subscribe();

    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    console.log(this.sort.direction)

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        tap(() => {
          this.data.loadCities(this.paginator.pageIndex, this.paginator.pageSize, this.sort.direction, this.input.nativeElement.value);
        })
      ).subscribe();
  }
}
