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
var gameoflife_service_1 = require('./gameoflife.service');
var http_1 = require('@angular/http');
var GameoflifeAppComponent = (function () {
    function GameoflifeAppComponent(gameOfLifeService) {
        this.gameOfLifeService = gameOfLifeService;
    }
    GameoflifeAppComponent.prototype.ngOnInit = function () {
        this.appName = "Game of Life - Simulation";
    };
    GameoflifeAppComponent.prototype.config = function () {
        var _this = this;
        this.gameOfLifeService.config(this.size, this.aliveIndexes)
            .subscribe(function (res) {
            _this.board = res;
        }, function (error) { return _this.errorMessage = error; });
    };
    GameoflifeAppComponent.prototype.calcNextGen = function () {
        var _this = this;
        this.gameOfLifeService.calcNextGen()
            .subscribe(function (res) {
            _this.board = res;
        }, function (error) { return _this.errorMessage = error; });
    };
    GameoflifeAppComponent.prototype.selectClass = function (cell) {
        var css = "";
        if (cell) {
            css = "alive";
        }
        else {
            css = "dead";
        }
        return css;
    };
    GameoflifeAppComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'gameoflife-app',
            templateUrl: 'gameoflife.component.html',
            styleUrls: ['gameoflife.component.css'],
            providers: [http_1.HTTP_PROVIDERS, gameoflife_service_1.GameOfLifeService]
        }), 
        __metadata('design:paramtypes', [gameoflife_service_1.GameOfLifeService])
    ], GameoflifeAppComponent);
    return GameoflifeAppComponent;
}());
exports.GameoflifeAppComponent = GameoflifeAppComponent;
//# sourceMappingURL=..\gameoflife.component.js.map