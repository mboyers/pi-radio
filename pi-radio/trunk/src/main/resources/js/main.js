app = angular.module("radio", []);

// Now Playing
app.controller("nowPlayingController", function($scope, $http) {
    
    $scope.update = function updateNowPlaying() {
        $http.get("/nowPlaying/current")
            .then(function(response) {
                $scope.nowPlaying = response.data;
            });
    }

    $scope.update();
});

// Calibrate
app.controller("calibrateController", function($scope, $http) {

    $http.get("/calibrate/availableTunePoints")
        .then(function(response) {
            $scope.availableTunePoints = response.data;
        });

    $scope.saveTunePoint = function saveTunePoint(tunePoint) {
        $http.post("/calibrate/tunePoint/" + tunePoint)
            .then(function(response) {
                $scope.status = response.data;
            });
    }

});

// Preview Station
app.controller("previewStationController", function($scope, $http) {

    $scope.previewStationUri = "";

    $scope.previewStation = function(stationUri) {
        $http({
            method : "POST",
            url : "/testStation/play",
            data: stationUri,
            contentType: 'text/plain'
        });
    }
});

// Station Configuration
app.controller("stationConfigurationController", function($scope, $http) {

    $http.get("/stationConfiguration/stations")
        .then(function(response) {
            $scope.stations = response.data;
        });

    $scope.saveStation = function(station) {
        console.log(station);
        $http({
            method : "POST",
            url : "/stationConfiguration",
            data: station
        });
    }
});