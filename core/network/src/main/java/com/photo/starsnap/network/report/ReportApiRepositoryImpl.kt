package com.photo.starsnap.network.report

import com.photo.starsnap.network.auth.dto.rs.SnapReportDto
import com.photo.starsnap.network.auth.dto.rs.UserReportDto
import com.photo.starsnap.network.dto.SliceResponseDto
import com.photo.starsnap.network.report.dto.rq.SnapReportCreateDto
import com.photo.starsnap.network.report.dto.rq.UserReportCreateDto
import javax.inject.Inject

class ReportApiRepositoryImpl @Inject constructor(
    val reportApi: ReportApi
): ReportRepository {
    override fun snapReport(snapReportCreateDto: SnapReportCreateDto) {
        return reportApi.snapReport(snapReportCreateDto)
    }

    override fun userReport(userReportCreateDto: UserReportCreateDto) {
        return reportApi.userReport(userReportCreateDto)
    }

    override fun getSnapReport(
        size: Int,
        page: Int,
    ): SliceResponseDto<SnapReportDto> {
        return reportApi.getSnapReport(size, page)
    }

    override fun getUserReport(
        size: Int,
        page: Int,
    ): SliceResponseDto<UserReportDto> {
        return reportApi.getUserReport(size, page)
    }
}