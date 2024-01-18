import { City } from "./City";

export interface CitiesResponse {
    content: City[];
    totalPages: number;
    totalElements: number;
}