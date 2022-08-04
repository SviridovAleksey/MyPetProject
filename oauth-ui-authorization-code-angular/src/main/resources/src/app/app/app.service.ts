import { Injectable } from '@angular/core';
import { Cookie } from 'ng2-cookies';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

export class Foo {
  constructor(
    public id: number,
    public name: string) { }
}

@Injectable()
export class AppService {
  public clientId = 'newClient';
  public redirectUri = 'http://localhost:8095/';

  constructor(
    private _http: HttpClient) { }

  retrieveToken(code) {
    let params = new URLSearchParams();
    params.append('grant_type', 'authorization_code');
    params.append('client_id', this.clientId);
    params.append('client_secret', 'newClientSecret');
    params.append('redirect_uri', this.redirectUri);
    params.append('code', code);

    let headers = new HttpHeaders({ 'Content-type': 'application/x-www-form-urlencoded; charset=utf-8' });
    this._http.post('http://localhost:8083/auth/realms/sviridov/protocol/openid-connect/token', params.toString(), { headers: headers })
      .subscribe(
        data => this.saveToken(data),
        err => err => alert('Invalid Credentials')
      );
  }

  updateTtoken() {
    let params = new URLSearchParams();
    params.append('grant_type', 'refresh_token');
    params.append('refresh_token', Cookie.get('refresh_token'));
    params.append('client_id', this.clientId);
    params.append('client_secret', 'newClientSecret');
    params.append('redirect_uri', this.redirectUri);


    let headers = new HttpHeaders({ 'Content-type': 'application/x-www-form-urlencoded; charset=utf-8' });
    this._http.post('http://localhost:8083/auth/realms/sviridov/protocol/openid-connect/token', params.toString(), { headers: headers })
      .subscribe(
        data => this.saveToken(data),
        err => alert('Invalid Update Token'),
      );
  }


  saveToken(token) {
    var expireDate = new Date().getTime() + (1000 * token.expires_in);
    Cookie.set("access_token", token.access_token, expireDate);
    Cookie.set("id_token", token.id_token, expireDate);
    Cookie.set("refresh_token", token.refresh_token)
    console.log('Obtained Access token');
    window.location.href = 'http://localhost:8095';
  }

  getResource(resourceUrl): Observable<any> {
    var headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      'Authorization': 'Bearer ' + Cookie.get('access_token'),
    });
    return this._http.get(resourceUrl, { headers })
      .catch((error: any) => this.errHandle(error));
  }

  putResource(resourceUrl, data): Observable<any> {

    var headers = new HttpHeaders({
      'Accept': 'application/json',
      'Content-type': 'application/json; charset=utf-8',
      'Authorization': 'Bearer ' + Cookie.get('access_token')
    });

    return this._http.put<any>(resourceUrl, data, { headers })
      .catch((error: any) => this.errHandle(error));
  }

  delResource(resourceUrl, id): Observable<any> {

    var headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      'Authorization': 'Bearer ' + Cookie.get('access_token'),
    });
    return this._http.delete(resourceUrl + '/' + id, { headers })
      .catch((error: any) => this.errHandle(error));

  }

  errHandle(error) {
    if (error.status === 401 || error.status === 403) {
      this.updateTtoken();
    } else {
      return Promise.reject(error)
    }
  }


  checkCredentials() {
    return Cookie.check('access_token');
  }

  logout() {
    let token = Cookie.get('id_token');
    Cookie.delete('access_token');
    Cookie.delete('id_token');
    Cookie.delete('refresh_token');
    let logoutURL = "http://localhost:8083/auth/realms/sviridov/protocol/openid-connect/logout?id_token_hint="
      + token
      + "&post_logout_redirect_uri=" + this.redirectUri;

    window.location.href = logoutURL;
  }
}
