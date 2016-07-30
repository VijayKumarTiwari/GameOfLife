"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var http_1 = require('@angular/http');
var Observable_1 = require('rxjs/Observable');
require('./rxjs-operators');
var GameoflifeService = (function () {
    function GameoflifeService(http) {
        this.http = http;
        this.baseUrl = "http://localhost:8080/board";
    }
    GameoflifeService.prototype.getScenarios = function () {
        return this.http.get(this.baseUrl)
            .map(this.extractListData)
            .catch(this.handleError);
    };
    GameoflifeService.prototype.handleError = function (error) {
        // In a real world app, we might use a remote logging infrastructure
        // We'd also dig deeper into the error to get a better message
        console.log(error);
        var errMsg = (error.message) ? error.message :
            error.status ? error.status + " - " + error.statusText : 'Server error';
        console.error(errMsg); // log to console instead
        return Observable_1.Observable.throw(errMsg);
    };
    GameoflifeService.prototype.extractListData = function (res) {
        var boards = res.json();
        return boards || [];
    };
    GameoflifeService.prototype.configNewBoard = function (size, initialAliveCells) {
        return this.http.post(this.baseUrl + "?size=" + size + "&indexes=" + initialAliveCells, null)
            .map(this.extractData)
            .catch(this.handleError);
    };
    GameoflifeService.prototype.extractData = function (res) {
        return res.json() || {};
    };
    GameoflifeService.prototype.loadBoard = function (id) {
        return this.http.get(this.baseUrl + "/" + id)
            .map(this.extractData)
            .catch(this.handleError);
    };
    GameoflifeService.prototype.calcNextGen = function (id) {
        return this.http.put(this.baseUrl + "/" + id, null)
            .map(this.extractData)
            .catch(this.handleError);
    };
    GameoflifeService.prototype.deleteBoard = function (id) {
        return this.http.delete(this.baseUrl + "/" + id)
            .catch(this.handleError);
    };
    GameoflifeService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], GameoflifeService);
    return GameoflifeService;
}());
exports.GameoflifeService = GameoflifeService;
//# sourceMappingURL=gameoflife.service.js.map