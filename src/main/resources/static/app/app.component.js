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
var GameBoard_1 = require('./GameBoard');
var http_1 = require('@angular/http');
var AppComponent = (function () {
    function AppComponent(gameOfLifeService) {
        this.gameOfLifeService = gameOfLifeService;
        this.title = 'app works!';
    }
    AppComponent.prototype.ngOnInit = function () {
        this.getBoards();
    };
    AppComponent.prototype.getBoards = function () {
        var _this = this;
        this.gameOfLifeService.getScenarios()
            .subscribe(function (boards) {
            _this.boards = boards;
        }),
            function (error) { return console.log(error); };
    };
    AppComponent.prototype.addNew = function () {
        this.board = new GameBoard_1.GameBoard();
        this.board.id = ":new";
    };
    AppComponent.prototype.configNewBoard = function () {
        var _this = this;
        this.gameOfLifeService.configNewBoard(this.board.size, this.board.initialAliveCells)
            .subscribe(function (board) {
            _this.board = board;
            _this.getBoards();
        }),
            function (error) { return console.log(error); };
    };
    AppComponent.prototype.loadBoard = function (id) {
        var _this = this;
        this.gameOfLifeService.loadBoard(id)
            .subscribe(function (board) {
            _this.board = board;
        }),
            function (error) { return console.log(error); };
    };
    AppComponent.prototype.calcNextGen = function (id) {
        var _this = this;
        this.gameOfLifeService.calcNextGen(id)
            .subscribe(function (board) {
            _this.board = board;
        }),
            function (error) { return console.log(error); };
    };
    AppComponent.prototype.deleteBoard = function (id) {
        var _this = this;
        this.gameOfLifeService.deleteBoard(id)
            .subscribe(function (board) {
            alert("deleted...");
            _this.board = null;
            _this.getBoards();
        }),
            function (error) { return console.log(error); };
    };
    AppComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'app-root',
            templateUrl: 'app.component.html',
            styleUrls: ['app.component.css'],
            providers: [http_1.HTTP_PROVIDERS, gameoflife_service_1.GameoflifeService]
        }), 
        __metadata('design:paramtypes', [gameoflife_service_1.GameoflifeService])
    ], AppComponent);
    return AppComponent;
}());
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map