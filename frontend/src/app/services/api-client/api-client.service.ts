import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
//TODO see getHost(), figure out
//import {environment} from '@env/environment';
import {forkJoin, mergeMap, Observable, of} from 'rxjs';
import {environment} from "../../environments/environment.dev";

@Injectable({
  providedIn: 'root'
})
export class ApiClientService {
  constructor(private readonly http: HttpClient) {
  }

  public get<T>(url: string, params?: HttpParams | { [param: string]: string | string[] }): Observable<T> {
    return this.http.get<T>(this.getHost() + url, {params});
  }

  public post<T, V>(url: string, data: V): Observable<T>;
  public post<T>(url: string, data: T): Observable<T> {
    return this.http.post<T>(this.getHost() + url, data);
  }

  public put<T extends { id: number }>(url: string, data: T): Observable<T> {
    return this.http.put<T>(this.getHost() + url, data);
  }

  public patch<T extends { id: number }>(url: string, data: object = {}): Observable<T> {
    return this.http.patch<T>(this.getHost() + url, data);
  }

  public delete(url: string, params?: { [param: string]: string | string[] }): Observable<any> {
    return this.http.delete(this.getHost() + url, params);
  }

  public options<T>(url: string, params?: { [param: string]: string | string[] }): Observable<T> {
    return this.http.options<T>(this.getHost() + url, params);
  }

  public getList<T>(ids: (number | string)[], url: string): Observable<(T | null)[]> {
    return 0 === ids.length
      ? of([])
      : of(ids).pipe(
        mergeMap(list => forkJoin([...list.map(id => (id ? this.get<T>(`${url}${id}/`) : of(null)))])),
      );
  }

  public deleteList(ids: number[], url: string): Observable<any> {
    return 0 === ids.length
      ? of([])
      : of(ids).pipe(mergeMap(list => forkJoin([...list.map(id => this.delete(`${url + id}/`))])));
  }

  public putList<T extends { id: number }>(dataList: T[], url: string): Observable<T[]> {
    return 0 === dataList.length
      ? of([])
      : of(dataList).pipe(
        mergeMap(list => forkJoin([...list.map((value: T) => this.put<T>(`${url}${value.id}/`, value))])),
      );
  }

  public patchList<T extends { id: number }>(dataList: T[], url: string): Observable<T[]> {
    return 0 === dataList.length
      ? of([])
      : of(dataList).pipe(
        mergeMap(list => forkJoin([...list.map((value: T) => this.patch<T>(`${url}${value.id}/`, value))])),
      );
  }

  public createList<T>(dataList: T[], url: string): Observable<T[]> {
    return 0 === dataList.length
      ? of([])
      : of(dataList).pipe(mergeMap(list => forkJoin([...list.map((value: T) => this.post<T, T>(url, value))])));
  }

  private getHost(): string {
    return environment.serverUrl
  }
}
