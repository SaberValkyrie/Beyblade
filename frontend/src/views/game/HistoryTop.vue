<template>
    <div class="container">
        <div class="row">
            <div class="col-12 mb-3 mb-lg-5">
                <div class="position-relative card table-nowrap table-card">
                    <div class="card-header align-items-center">
                        <h5 class="mb-0">Lịch Sử Xếp Hạng</h5>
                    </div>
                    <div class="table-responsive">
                        <table class="table mb-0">
                            <thead class="small text-uppercase bg-body text-muted">
                                <tr>
                                    <th>Thời Gian</th>
                                    <th>Người Thách Đấu</th>
                                    <th>Người Giữ Top</th>
                                    <th>Trạng Thái</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr class="align-middle">
                                    <td>13 Sep, 2021</td>
                                    <td>Renee Sims</td>
                                    <td>haidz</td>
                                    <td>
                                        <span class="badge fs-6 fw-normal bg-tint-success text-success">Completed</span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </template>
    
    <style>
    body {
        margin-top: 20px;
        background: #FFF5EE;
    }
    
    .card {
        box-shadow: 0 20px 27px 0 rgb(0 0 0 / 5%);
    }
    
    .avatar.sm {
        width: 2.25rem;
        height: 2.25rem;
        font-size: .818125rem;
    }
    
    .table-nowrap .table td,
    .table-nowrap .table th {
        white-space: nowrap;
    }
    
    .table>:not(caption)>*>* {
        padding: 0.75rem 1.25rem;
        border-bottom-width: 1px;
    }
    
    table th {
        font-weight: 600;
        background-color: #eeecfd !important;
    }
    
    .fa-arrow-up {
        color: #00CED1;
    }
    
    .fa-arrow-down {
        color: #FF00FF;
    }
    
    /* Responsive styles */
    @media (max-width: 768px) {
        .card {
            margin: 0 10px;
        }
    
        .table-responsive {
            width: 100%;
            overflow-x: auto;
        }
    
        .table {
            width: 100%;
        }
    
        table th, table td {
            padding: 0.5rem;
        }
    
        table th {
            font-size: 0.875rem;
        }
    
        table td {
            font-size: 0.875rem;
        }
    
        .badge {
            font-size: 0.75rem;
        }
    }
    </style>
    