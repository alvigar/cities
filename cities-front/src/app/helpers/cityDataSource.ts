import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { City } from "../interfaces/City";
import { BehaviorSubject, Observable, catchError, finalize, of } from "rxjs";
import { CitiesService } from "../services/cities.service";

export class CityDataSource implements DataSource<City> {

    private citySubject = new BehaviorSubject<City[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();
    public length = 100;

    constructor(private citiesService: CitiesService) {}

    connect(collectionViewer: CollectionViewer): Observable<readonly City[]> {
        return this.citySubject.asObservable();
    }
    disconnect(collectionViewer: CollectionViewer): void {
        this.citySubject.complete();
        this.loadingSubject.complete();
    }
    
    loadCities(pageIndex = 0, pageSize = 20) {
        this.loadingSubject.next(true);

        this.citiesService.queryByPage(pageIndex, pageSize).pipe(catchError(() => of([])), finalize(() => this.loadingSubject.next(false))).subscribe(cities => {
            this.length = cities.length;
            this.citySubject.next(cities);
        })
    }
}