'use strict';

angular.module('travelappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bucket', {
                parent: 'entity',
                url: '/buckets',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Buckets'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bucket/buckets.html',
                        controller: 'BucketController'
                    }
                },
                resolve: {
                }
            })
            .state('bucket.detail', {
                parent: 'entity',
                url: '/bucket/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Bucket'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bucket/bucket-detail.html',
                        controller: 'BucketDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Bucket', function($stateParams, Bucket) {
                        return Bucket.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bucket.new', {
                parent: 'bucket',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bucket/bucket-dialog.html',
                        controller: 'BucketDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    description: null,
                                    imageUrls: null,
                                    peopleCount: null,
                                    people: null,
                                    achievedCount: null,
                                    achieve: null,
                                    experiences: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('bucket', null, { reload: true });
                    }, function() {
                        $state.go('bucket');
                    })
                }]
            })
            .state('bucket.edit', {
                parent: 'bucket',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bucket/bucket-dialog.html',
                        controller: 'BucketDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Bucket', function(Bucket) {
                                return Bucket.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bucket', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('bucket.delete', {
                parent: 'bucket',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bucket/bucket-delete-dialog.html',
                        controller: 'BucketDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Bucket', function(Bucket) {
                                return Bucket.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bucket', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
