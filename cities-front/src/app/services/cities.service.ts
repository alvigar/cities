import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { City } from "../interfaces/City";
import { Injectable } from "@angular/core";

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class CitiesService {
    private citiesUrl: string;

    constructor(private http: HttpClient) {
        this.citiesUrl = 'http://localhost:1111/api/cities';
    }

    public queryByPage(page: number, size: number): Observable<City[]> {
        return this.http.get<City[]>(`${this.citiesUrl}?page=${page}&size=${size}`, httpOptions);
    }
}