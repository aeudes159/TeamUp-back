package main.java.io.takima.teamupback.activityFeed

import main.java.io.takima.teamupback.common.dao.BaseDao
import org.springframework.stereotype.Component

/**
 * ActivityFeedDao - Extends BaseDao for common pagination methods.
 */
@Component
class ActivityFeedDao : BaseDao<ActivityFeed>(ActivityFeed::class) {

    override fun getDefaultOrderBy(): String = "e.id"
}
