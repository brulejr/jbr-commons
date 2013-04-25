var applModule = angular.module('taskmgr', []);

applModule.controller('TaskCtrl', [ '$scope', function($scope) {
	$scope.tasks = [ {
		text : 'learn angular',
		done : true
	}, {
		text : 'build an angular app',
		done : false
	} ];

	$scope.addTask = function() {
		$scope.tasks.push({
			text : $scope.taskText,
			done : false
		});
		$scope.todoText = '';
	};

	$scope.remaining = function() {
		var count = 0;
		angular.forEach($scope.tasks, function(task) {
			count += task.done ? 0 : 1;
		});
		return count;
	};

	$scope.archive = function() {
		var oldTasks = $scope.tasks;
		$scope.tasks = [];
		angular.forEach(oldTasks, function(task) {
			if (!task.done)
				$scope.tasks.push(task);
		});
	};
} ]);
