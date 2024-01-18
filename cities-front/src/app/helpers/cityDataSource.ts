import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { City } from "../interfaces/City";
import { BehaviorSubject, Observable, catchError, finalize, of } from "rxjs";
import { CitiesService } from "../services/cities.service";
import { CitiesResponse } from "../interfaces/CitiesResponse";

export class CityDataSource implements DataSource<City> {

    private citySubject = new BehaviorSubject<City[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();
    public length = 100;
    public totalPages = 100;

    constructor(private citiesService: CitiesService) {}

    connect(collectionViewer: CollectionViewer): Observable<readonly City[]> {
        return this.citySubject.asObservable();
    }
    disconnect(collectionViewer: CollectionViewer): void {
        this.citySubject.complete();
        this.loadingSubject.complete();
    }
    
    loadCities(pageIndex = 0, pageSize = 20, sortDirection = 'asc', filter = null) {
        this.loadingSubject.next(true);

        this.citiesService.queryByPage(pageIndex, pageSize, sortDirection, filter).pipe(catchError(() => of([])), finalize(() => this.loadingSubject.next(false))).subscribe(citiesResponse => {
            this.citySubject.next((citiesResponse as CitiesResponse).content);
            this.length = (citiesResponse as CitiesResponse).totalElements;
            this.totalPages = (citiesResponse as CitiesResponse).totalPages;
        })
    }
}