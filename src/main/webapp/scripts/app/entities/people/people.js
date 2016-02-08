'use strict';

angular.module('travelappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('people', {
                parent: 'entity',
                url: '/peoples',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Peoples'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/people/peoples.html',
                        controller: 'PeopleController'
                    }
                },
                resolve: {
                }
            })
            .state('people.detail', {
                parent: 'entity',
                url: '/people/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'People'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/people/people-detail.html',
                        controller: 'PeopleDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'People', function($stateParams, People) {
                        return People.get({id : $stateParams.id});
                    }]
                }
            })
            .state('people.new', {
                parent: 'people',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/people/people-dialog.html',
                        controller: 'PeopleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    followersCount: null,
                                    followingCount: null,
                                    bucketCount: null,
                                    bucketAchievedCount: null,
                                    followers: null,
                                    following: null,
                                    buckets: null,
                                    bucketAchieved: null,
                                    rating: null,
                                    userId: null,
                                    blogsCount: null,
                                    blogs: null,
                                    imageUrl: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('people', null, { reload: true });
                    }, function() {
                        $state.go('people');
                    })
                }]
            })
            .state('people.edit', {
                parent: 'people',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/people/people-dialog.html',
                        controller: 'PeopleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['People', function(People) {
                                return People.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('people', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('people.delete', {
                parent: 'people',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/people/people-delete-dialog.html',
                        controller: 'PeopleDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['People', function(People) {
                                return People.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('people', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
