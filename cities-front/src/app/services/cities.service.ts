import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { City } from "../interfaces/City";
import { Injectable } from "@angular/core";
import { CitiesResponse } from "../interfaces/CitiesResponse";

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class CitiesService {
    private citiesUrl: string;

    constructor(private http: HttpClient) {
        this.citiesUrl = 'http://localhost:1111/api/cities';
    }

    public queryByPage(page: number, size: number, sort: string, filter = null): Observable<CitiesResponse> {
        return this.http.get<CitiesResponse>(`${this.citiesUrl}/queryByPage?page=${page}&size=${size}&sort=name,${sort}${filter !== null ? `&filter=${filter}` : ''}`, httpOptions);
    }
}